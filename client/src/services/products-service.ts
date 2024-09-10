import { ApiService } from '@/services/api-service'
import { useSessionStore } from '@/stores/session-store'
import type {
  CreateProductDto,
  Product,
  ProductsResponseDto,
  UpdateProductDto
} from '@/model/api/products-model'
import { error, errorWithData, loading, loadingWithData, success } from '@/model/remote-data-model'
import type { AxiosResponse } from 'axios'

export class ProductsService {
  constructor(readonly apiService: ApiService) {}

  public async fetchProducts(): Promise<void> {
    const { products, setProducts } = useSessionStore().stores.products()

    try {
      if (products?.data) {
        setProducts(loadingWithData(products.data))
      } else {
        setProducts(loading())
      }

      const response = await this.apiService.client.get<ProductsResponseDto>('/products')

      setProducts(success(response.data.products))
    } catch (error_: unknown) {
      console.error('Failed to load products data.', error_)

      if (products?.data) {
        setProducts(errorWithData('Failed to load products data.', products.data))
      } else {
        setProducts(error('Failed to load products data.'))
      }

      throw error_
    }
  }

  public async createProduct(data: CreateProductDto): Promise<Product> {
    try {
      return (
        await this.apiService.client.post<CreateProductDto, AxiosResponse<Product>>(
          '/products',
          data
        )
      ).data
    } catch (error: unknown) {
      console.error('Failed to create product.')
      throw error
    }
  }

  public async updateProduct(id: string, data: UpdateProductDto): Promise<Product> {
    try {
      return (
        await this.apiService.client.put<UpdateProductDto, AxiosResponse<Product>>(
          `/products/${id}`,
          data
        )
      ).data
    } catch (error: unknown) {
      console.error('Failed to update product.')
      throw error
    }
  }

  public async deleteProduct(id: string): Promise<void> {
    try {
      await this.apiService.client.delete<AxiosResponse<void>>(`/products/${id}`)
    } catch (error: unknown) {
      console.error('Failed to delete product.')
      throw error
    }
  }
}

export const productsServiceKey = Symbol()
