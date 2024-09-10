import '@picocss/pico/css/pico.blue.min.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { AuthService, authServiceKey } from '@/services/auth-service'
import { ApiService } from '@/services/api-service'
import { ProductsService, productsServiceKey } from '@/services/products-service'

const app = createApp(App)

// plugins
app.use(createPinia())
app.use(router)

// services
const apiService = new ApiService()
const authService = new AuthService(apiService)
const productsService = new ProductsService(apiService)

app.provide(authServiceKey, authService)
app.provide(productsServiceKey, productsService)

// start
app.mount('#app')

// bootstrapping
await Promise.all([authService.restoreSession()])
