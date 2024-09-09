<script setup lang="ts">
import { useSessionStore } from '@/stores/session-store'
import { AuthService, authServiceKey } from '@/services/auth-service'
import { injectStrict } from '@/services/inject-service'

const authService: AuthService = injectStrict(authServiceKey)

function userClickedLogoutButton() {
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

  <slot></slot>
</template>
