<script setup lang="ts">
import { AuthService, authServiceKey } from '@/services/auth-service'
import { injectStrict } from '@/services/inject-service'
import { ref } from 'vue'

const authService: AuthService = injectStrict(authServiceKey)

const shouldDestroyDom = ref(false)

function userClickedLogoutButton() {
  // Clear DOM in order to avoid errors for inconsistent state when user logs out
  // e.g. "session-store.ts:12 Uncaught (in promise) Error: Cannot retrieve logged in session if the session is not in logged in state"
  shouldDestroyDom.value = true

  authService.logoutUser()
}
</script>

<template>
  <nav>
    <ul>
      <li><strong>Coding Task</strong></li>
    </ul>
    <ul>
      <li>
        <RouterLink to="/">Products</RouterLink>
      </li>
      <li>
        <a @click="userClickedLogoutButton">Logout</a>
      </li>
    </ul>
  </nav>

  <div v-if="!shouldDestroyDom">
    <slot></slot>
  </div>
</template>
