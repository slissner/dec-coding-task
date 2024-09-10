export interface ProductsResponseDto {
  products: Product[]
}

export interface Product {
  id: string
  name: string
  price: number
}
