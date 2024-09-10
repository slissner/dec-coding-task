import type { AuthRequestDto, AuthResponseDto } from '@/model/api/auth-model'
import { useSessionStore } from '@/stores/session-store'
import type { AxiosResponse } from 'axios'
import router from '@/router'
import { ApiService } from '@/services/api-service'

export class AuthService {
  private readonly localStorageKey = 'auth'

  private readonly sessionStore = useSessionStore()

  constructor(readonly apiService: ApiService) {}

  public async authenticateUser(email: string, password: string): Promise<void> {
    try {
      const response = await this.apiService.client.post<
        AuthRequestDto,
        AxiosResponse<AuthResponseDto>
      >('/auth', {
        email,
        password
      })

      const { token } = response.data

      if (token == null || token.length === 0) {
        throw new Error('Token was empty – aborting')
      }

      await this.doLoginUser(response.data.email, token)
    } catch (error: unknown) {
      console.error('Failed to login user, rolling back auth...')
      await this.logoutUser()
      throw error
    }
  }

  public async restoreSession() {
    const authJsonRaw = localStorage.getItem(this.localStorageKey)

    if (authJsonRaw == null || authJsonRaw.length <= 0) {
      return
    }

    try {
      const { email, token } = JSON.parse(authJsonRaw)

      // TODO Check already in the client if token is expired before using it.

      await this.doLoginUser(email, token)
    } catch (error) {
      console.debug('Could not restore auth session from storage – ignoring.', error)
    }
  }

  private async doLoginUser(email: string, token: string) {
    this.sessionStore.loginUser(email, token)

    localStorage.setItem(this.localStorageKey, JSON.stringify({ email, token }))

    this.apiService.setAuthHeader(token)

    await router.push({ path: '/' })
  }

  public async logoutUser() {
    this.sessionStore.logoutUser()

    localStorage.removeItem(this.localStorageKey)

    this.apiService.clearAuthHeader()

    await router.push({ name: 'login' })
  }
}

export const authServiceKey = Symbol()
