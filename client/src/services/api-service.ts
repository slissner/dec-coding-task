import axios, { type AxiosInstance } from 'axios'

export class ApiService {
  private readonly axiosInstance = axios.create({
    baseURL: 'http://localhost:5173/api',
    timeout: 5000
  })

  get client(): AxiosInstance {
    return this.axiosInstance
  }

  public setAuthHeader(token: string): void {
    this.axiosInstance.defaults.headers.common['Authorization'] = `Bearer ${token}`
  }

  public clearAuthHeader(): void {
    delete this.axiosInstance.defaults.headers.common['Authorization']
  }
}
