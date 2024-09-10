<script setup lang="ts">
import LoggedInLayout from '@/components/layout/LoggedInLayout.vue'
import { computed, onMounted } from 'vue'
import { injectStrict } from '@/services/inject-service'
import { ProductsService, productsServiceKey } from '@/services/products-service'
import { useSessionStore } from '@/stores/session-store'
import ProductsTable from '@/components/products/ProductsTable.vue'

const products = computed(() => useSessionStore().stores.products().products)

onMounted(async () => {
  const productsService: ProductsService = injectStrict(productsServiceKey)

  await productsService.fetchProducts()
})
</script>

<template>
  <LoggedInLayout>
    <main>
      <ProductsTable :products="products" />
    </main>
  </LoggedInLayout>
</template>

<style scoped></style>
