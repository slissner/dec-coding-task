<script setup lang="ts">
import type { Product } from '@/model/api/products-model'
import type { RemoteData } from '@/model/remote-data-model'

const props = defineProps<{
  products: RemoteData<Product[], string>
}>()
</script>

<template>
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
</template>

<style scoped>
.error-text {
  color: red;
}

.price {
  text-align: right;
}
</style>
