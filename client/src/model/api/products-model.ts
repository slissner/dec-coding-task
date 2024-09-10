export interface ProductsResponseDto {
  products: Product[]
}

export interface Product {
  id: string
  name: string
  price: number
}

export interface CreateProductDto {
  name: string
  price: number
}

export interface UpdateProductDto {
  name: string
  price: number
}
