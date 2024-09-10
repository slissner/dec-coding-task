<script setup lang="ts">
import LoggedInLayout from '@/components/layout/LoggedInLayout.vue'
import { computed, onMounted, toRefs } from 'vue'
import { injectStrict } from '@/services/inject-service'
import { ProductsService, productsServiceKey } from '@/services/products-service'
import { useSessionStore } from '@/stores/session-store'
import ProductsTable from '@/components/products/ProductsTable.vue'
import ProductsForm from '@/components/products/ProductsForm.vue'
import router from '@/router'
import type { Product } from '@/model/api/products-model'
import Websocket from '@/components/websocket/Websocket.vue'

const productsService: ProductsService = injectStrict(productsServiceKey)

const props = defineProps<{
  // from router
  productId?: string
}>()

const { productId } = toRefs(props)

const products = computed(() => useSessionStore().stores.products().products)

const product = computed(() => useSessionStore().stores.products().findProduct(productId.value))

onMounted(async () => {
  await fetchProducts()
})

async function userClickedEditProductButton(id: string) {
  await router.push({ path: `/products/${id}` })
}

async function userClickedDeleteProductButton(id: string) {
  await productsService.deleteProduct(id)
  await fetchProducts()
}

async function socketLastUpdatedAtChanged(userId: string, lastChangedAt: Date) {
  // TODO avoid double fetching if the same user has just changed the products data by comparing userId and lastChangedAt timestamp.
  console.debug(
    `User changed products data - fetching products now. [userId=${userId}, lastChangedAt=${lastChangedAt}]`
  )

  await fetchProducts()
}

async function fetchProducts() {
  await productsService.fetchProducts()
}
</script>

<template>
  <LoggedInLayout>
    <main>
      <Websocket @socket-last-updated-changed="socketLastUpdatedAtChanged" />
      <ProductsForm :product="product" @products-have-changed="fetchProducts" />
      <hr />
      <ProductsTable
        :products="products"
        @user-clicked-edit-product-button="userClickedEditProductButton"
        @user-clicked-delete-product-button="userClickedDeleteProductButton"
      />
    </main>
  </LoggedInLayout>
</template>

<style scoped></style>
