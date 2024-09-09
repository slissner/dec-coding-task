import { ref, computed, type Ref } from 'vue'
import { defineStore } from 'pinia'
import type { SessionModel } from '@/model/session-model'
import { initSession, makeLoggedInSession } from '@/services/session-service'

export const useSessionStore = defineStore('session', () => {
  const session: Ref<SessionModel, SessionModel> = ref(initSession())

  const isUserLoggedIn = computed(() => session.value.state === 'loggedIn')

  const loginUser = (email: string, token: string) => {
    session.value = makeLoggedInSession(email, token)
  }

  const logoutUser = () => {
    session.value = initSession();
  }

  return { session, isUserLoggedIn, loginUser, logoutUser }
})
