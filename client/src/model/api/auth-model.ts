export interface AuthRequestDto {
  email: string
  password: string
}

export interface AuthResponseDto {
  email: string
  token: string
}
