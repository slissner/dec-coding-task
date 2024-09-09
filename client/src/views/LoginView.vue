<template>
  <main>
    <div class="container-fluid login-form">
      <form>
        <h1>Login</h1>

        <fieldset>
          <label>
            E-Mail
            <input
              name="email"
              placeholder="E-Mail"
              autocomplete="email"
            />
          </label>
          <label>
            Password
            <input
              type="password"
              name="password"
              placeholder=""
              autocomplete="password"
            />
          </label>
        </fieldset>

        <input
          type="submit"
          value="Sign in"
        />
      </form>
    </div>
  </main>
</template>

<style scoped>
.login-form {
  max-width: 480px;
}
</style>
<script setup lang="ts">
</script>
