<script setup lang="ts">
import LoggedInLayout from '@/components/layout/LoggedInLayout.vue'
import { computed, onMounted } from 'vue'
import { injectStrict } from '@/services/inject-service'
import { ProductsService, productsServiceKey } from '@/services/products-service'
import { useSessionStore } from '@/stores/session-store'

const products = computed(() => useSessionStore().stores.products().products)

onMounted(async () => {
  const productsService: ProductsService = injectStrict(productsServiceKey)

  await productsService.fetchProducts()
})
</script>

<template>
  <LoggedInLayout>
    <main>
      <div v-if="products.state === 'loading'">
        <span aria-busy="true">Loading products...</span>
      </div>
      <div v-if="products.state === 'error'">
        <span class="error-text">Failed to load products.</span>
      </div>
      <div v-if="products?.data">
        <div v-if="products.state === 'loading'">
          <span aria-busy="true">Reloading products...</span>
        </div>
        <table>
          <thead>
            <tr>
              <td>Name</td>
              <td>Price</td>
            </tr>
          </thead>
          <tbody>
            <tr v-for="product in products.data" :key="product.id">
              <th>{{ product.name }}</th>
              <th class="price">{{ product.price.toFixed(2) }} €</th>
            </tr>
          </tbody>
        </table>
      </div>
    </main>
  </LoggedInLayout>
</template>

<style scoped>
.error-text {
  color: red;
}

.price {
  text-align: right;
}
</style>
