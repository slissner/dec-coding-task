<script setup lang="ts">
import { ref } from 'vue'
import { type AuthService, authServiceKey } from '@/services/auth-service'
import { injectStrict } from '@/services/inject-service'

const authService: AuthService = injectStrict(authServiceKey)

const email = ref('')
const password = ref('')

const isRequestRunning = ref(false)
const authenticationErrorText = ref<string | null>(null)

async function userSubmittedForm(event: Event) {
  event.preventDefault()

  // TODO Skip programmatic validation and rely on html validation for now...

  console.log('User submitted form.', email.value, password.value)

  try {
    isRequestRunning.value = true
    authenticationErrorText.value = null

    await authService.authenticateUser(email.value, password.value)
  } catch (error) {
    console.error('Failed to authenticate user.', error)

    authenticationErrorText.value = 'Failed to log in user!'
  } finally {
    isRequestRunning.value = false
  }
}
</script>

<template>
  <main>
    <div class="container-fluid login-form">
      <form @submit="userSubmittedForm">
        <h1>Login</h1>

        <fieldset>
          <label>
            E-Mail
            <input
              type="email"
              name="email"
              v-model="email"
              placeholder="E-Mail"
              autocomplete="email"
              required
            />
          </label>
          <label>
            Password
            <input
              type="password"
              name="password"
              v-model="password"
              placeholder=""
              autocomplete="password"
              required
            />
          </label>
        </fieldset>

        <input type="submit" value="Sign in" :disabled="isRequestRunning" />

        <div class="authentication-error" v-if="authenticationErrorText != null">
          {{ authenticationErrorText }}
        </div>
      </form>
    </div>
  </main>
</template>

<style scoped>
.login-form {
  max-width: 480px;
}

.authentication-error {
  color: red;
}
</style>
