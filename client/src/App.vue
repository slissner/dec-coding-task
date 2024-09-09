<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <main class="container">
    <nav>
      <ul>
        <li><strong>Coding Task</strong></li>
      </ul>
      <ul>
        <li>
          <RouterLink to="/">Home</RouterLink>
        </li>
      </ul>
    </nav>

    <RouterView />
  </main>
</template>

<style scoped>
</style>
