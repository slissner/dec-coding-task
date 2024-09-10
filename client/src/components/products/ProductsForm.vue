<script setup lang="ts">
import { ref, watch } from 'vue'
import { injectStrict } from '@/services/inject-service'
import { ProductsService, productsServiceKey } from '@/services/products-service'
import type { CreateProductDto, Product, UpdateProductDto } from '@/model/api/products-model'
import router from '@/router'

const productsService: ProductsService = injectStrict(productsServiceKey)

const props = defineProps<{
  product?: Product
}>()

const emit = defineEmits(['products-have-changed'])

const name = ref('')
const price = ref(0.0)

const isRequestRunning = ref(false)
const formFeedbackText = ref<string | null>(null)

watch(
  () => props?.product,
  (newProduct) => {
    if (newProduct) {
      name.value = newProduct.name
      price.value = newProduct.price
    } else {
      resetForm()
    }
  }
)

async function userSubmittedForm(event: Event) {
  event.preventDefault()

  const data = { name: name.value, price: price.value }

  if (props?.product) {
    await updateProduct(props.product.id, data)
  } else {
    await createProduct(data)
  }

  emit('products-have-changed')
}

async function createProduct(data: CreateProductDto) {
  try {
    formFeedbackText.value = null
    isRequestRunning.value = true

    await productsService.createProduct(data)

    formFeedbackText.value = 'Product created.'

    resetForm()
  } catch (error) {
    console.error('Error creating product.', error)
    formFeedbackText.value = 'Error creating product'
  } finally {
    isRequestRunning.value = false
  }
}

async function updateProduct(id: string, data: UpdateProductDto) {
  try {
    formFeedbackText.value = null
    isRequestRunning.value = true

    await productsService.updateProduct(id, data)

    formFeedbackText.value = 'Product updated.'
  } catch (error) {
    console.error('Error updating product.', error)
    formFeedbackText.value = 'Error updating product'
  } finally {
    isRequestRunning.value = false
  }
}

async function userClickedResetForm() {
  resetForm()
  await router.push({ name: 'products' })
}

function resetForm() {
  name.value = ''
  price.value = 0.0
  isRequestRunning.value = false
  formFeedbackText.value = null
}
</script>

<template>
  <form @submit="userSubmittedForm">
    <h2 v-if="product?.id != null">Edit Product</h2>
    <h2 v-else>Create Product</h2>

    <fieldset>
      <label>
        Name
        <input type="text" name="name" v-model="name" placeholder="Product Name" required />
      </label>
      <label>
        Price
        <input
          type="number"
          name="price"
          v-model="price"
          placeholder="499.99"
          step="0.01"
          pattern="[0-9]+(\.[0-9][0-9]?)?"
          required
        />
      </label>
    </fieldset>

    <input type="submit" value="Edit" v-if="product?.id != null" :disabled="isRequestRunning" />
    <input
      class="secondary outline"
      type="reset"
      value="Cancel"
      v-if="product?.id != null"
      :disabled="isRequestRunning"
      @click="userClickedResetForm"
    />
    <input type="submit" value="Create" v-else :disabled="isRequestRunning" />

    <div v-if="formFeedbackText != null">
      {{ formFeedbackText }}
    </div>
  </form>
</template>

<style scoped></style>
