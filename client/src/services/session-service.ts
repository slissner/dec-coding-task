import type { LoggedIn, LoggedOut  } from '@/model/session-model'

export function initSession(): LoggedOut {
  return { state: 'loggedOut' }
}

export function makeLoggedInSession(email: string, token: string): LoggedIn {
  return { state: 'loggedIn', email, token }
}
