import '@picocss/pico/css/pico.blue.min.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'
import { AuthService, authServiceKey } from '@/services/auth-service'

const app = createApp(App)

// plugins
app.use(createPinia())
app.use(router)

// services
const authService = new AuthService()
app.provide(authServiceKey, authService)

// pre-start bootstrapping
authService.restoreSession()

// start
app.mount('#app')
