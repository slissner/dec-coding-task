import { makeProductsStore } from '@/stores/products-store'

export type SessionModel = LoggedOut | LoggedIn

export interface LoggedOut {
  state: 'loggedOut'
}

export interface LoggedIn {
  state: 'loggedIn'
  email: string
  token: string
  stores: {
    products: ReturnType<typeof makeProductsStore>
  }
}

export function initSession(): LoggedOut {
  return { state: 'loggedOut' }
}

export function makeLoggedInSession(email: string, token: string): LoggedIn {
  return { state: 'loggedIn', email, token, stores: { products: makeProductsStore() } }
}
