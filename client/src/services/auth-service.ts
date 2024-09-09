import apiClient from '@/services/api-service'
import type { AuthRequestDto, AuthResponseDto } from '@/model/api/auth-model'
import { useSessionStore } from '@/stores/session-store'
import type { AxiosResponse } from 'axios'
import router from '@/router'

export class AuthService {
  private readonly localStorageKey = 'auth'

  private readonly sessionStore = useSessionStore()

  public async authenticateUser(email: string, password: string): Promise<void> {
    const response = await apiClient.post<AuthRequestDto, AxiosResponse<AuthResponseDto>>('/auth', {
      email,
      password
    })

    const { token } = response.data

    if (token == null || token.length === 0) {
      throw new Error('Token was empty – aborting')
    }

    this.sessionStore.loginUser(response.data.email, token)

    localStorage.setItem(
      this.localStorageKey,
      JSON.stringify({ email: response.data.email, token })
    )

    await router.push({ path: '/' })
  }

  public restoreSession() {
    const authJsonRaw = localStorage.getItem(this.localStorageKey)

    if (authJsonRaw == null || authJsonRaw.length <= 0) {
      return
    }

    try {
      const { email, token } = JSON.parse(authJsonRaw)

      const sessionStore = useSessionStore()

      sessionStore.loginUser(email, token)
    } catch (error) {
      console.debug('Could not restore auth session from storage – ignoring.', error)
    }
  }

  public async logoutUser() {
    this.sessionStore.logoutUser()
    localStorage.removeItem(this.localStorageKey)

    await router.push({ name: 'login' })
  }
}

export const authServiceKey = Symbol()
