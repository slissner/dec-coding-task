<script setup lang="ts">
import type { Product } from '@/model/api/products-model'
import type { RemoteData } from '@/model/remote-data-model'

const props = defineProps<{
  products: RemoteData<Product[], string>
}>()

defineEmits<{
  (e: 'user-clicked-edit-product-button', id: string): void
  (e: 'user-clicked-delete-product-button', id: string): void
}>()
</script>

<template>
  <hgroup class="headline-group">
    <h2>Products</h2>
    <p v-if="products.state === 'loading' && products.data == null">
      <span aria-busy="true">Loading products...</span>
    </p>
    <p v-if="products.state === 'loading' && products.data != null">
      <span aria-busy="true">Reloading products...</span>
    </p>
    <p v-if="products.state === 'error'">
      <span class="error-text">Failed to load products.</span>
    </p>
  </hgroup>

  <div v-if="products?.data">
    <table>
      <thead>
        <tr>
          <td>Name</td>
          <td>Price</td>
          <td>Options</td>
        </tr>
      </thead>
      <tbody>
        <tr v-for="product in products.data" :key="product.id">
          <th>{{ product.name }}</th>
          <th class="price">{{ product.price.toFixed(2) }} €</th>
          <th>
            <button @click="$emit('user-clicked-edit-product-button', product.id)">Edit</button>
            <button
              class="secondary"
              style="margin-left: 8px"
              @click="$emit('user-clicked-delete-product-button', product.id)"
            >
              Delete
            </button>
          </th>
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

.headline-group {
  /* to avoid jumpy UI while loading */
  min-height: 60px;
}
</style>
