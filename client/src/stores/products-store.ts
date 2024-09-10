import { computed, ref, type Ref } from 'vue'
import { defineStore } from 'pinia'
import type { Product } from '@/model/api/products-model'
import { loading, type RemoteData } from '@/model/remote-data-model'

/**
 * Access this store through the session.stores property!
 */
export const makeProductsStore = () =>
  defineStore('products', () => {
    const products: Ref<RemoteData<Product[], string>, RemoteData<Product[], string>> = ref(
      loading()
    )

    const findProduct = computed(() => (productId: string | undefined) => {
      if (productId == null) {
        return undefined
      }

      return products.value.data?.find((product) => product.id === productId)
    })

    const setProducts = (newProducts: RemoteData<Product[], string>) => {
      products.value = newProducts
    }

    return { products, findProduct, setProducts }
  })
