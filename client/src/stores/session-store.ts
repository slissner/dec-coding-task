import { ref, computed, type Ref } from 'vue'
import { defineStore } from 'pinia'
import { initSession, makeLoggedInSession, type SessionModel } from '@/model/session-model'

export const useSessionStore = defineStore('session', () => {
  const session: Ref<SessionModel, SessionModel> = ref(initSession())

  const isUserLoggedIn = computed(() => session.value.state === 'loggedIn')

  const loggedInSession = computed(() => {
    if (session.value.state !== 'loggedIn') {
      throw new Error('Cannot retrieve logged in session if the session is not in logged in state')
    }

    return session.value
  })

  const stores = computed(() => {
    return loggedInSession.value.stores
  })

  const loginUser = (email: string, token: string) => {
    session.value = makeLoggedInSession(email, token)
  }

  const logoutUser = () => {
    session.value = initSession()
  }

  return { isUserLoggedIn, loggedInSession, stores, loginUser, logoutUser }
})
