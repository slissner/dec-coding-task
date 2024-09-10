export type RemoteData<D, E> = Loading<D> | Success<D> | Error<E, D>

export interface Loading<D> {
  state: 'loading'
  data?: D
}

export interface Success<D> {
  state: 'success'
  data: D
}

export interface Error<E, D> {
  state: 'error'
  error: E
  data?: D
}

export function loading(): RemoteData<never, never> {
  return { state: 'loading' }
}

export function loadingWithData<D>(data: D): RemoteData<D, never> {
  return { state: 'loading', data }
}

export function success<D>(data: D): RemoteData<D, never> {
  return { state: 'success', data }
}

export function error<E, D>(error: E): RemoteData<never, E> {
  return { state: 'error', error }
}

export function errorWithData<E, D>(error: E, data: D): RemoteData<D, E> {
  return { state: 'error', error, data }
}
