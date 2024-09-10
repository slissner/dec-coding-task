import { ApiService } from '@/services/api-service'
import { useSessionStore } from '@/stores/session-store'
import type { ProductsResponseDto } from '@/model/api/products-model'
import { loading, success, error, loadingWithData, errorWithData } from '@/model/remote-data-model'

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
    }
  }
}

export const productsServiceKey = Symbol()
