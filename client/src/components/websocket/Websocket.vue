<script setup lang="ts">
import { onMounted, onUnmounted, ref } from 'vue'
import type { Ref } from 'vue'
import type { WebsocketState } from '@/model/websocket-model'

const emit = defineEmits<{
  (e: 'socket-last-updated-changed', userId: string, lastUpdatedAt: Date): void
}>()

const websocketState: Ref<WebsocketState, WebsocketState> = ref('loading')

let websocket: WebSocket | null = null

onMounted(() => {
  // Create a WebSocket connection
  websocket = new WebSocket('ws://localhost:5173/ws')

  websocket.onopen = function () {
    websocketState.value = 'opened'
  }

  // On message received from server
  websocket.onmessage = function (event) {
    const data = JSON.parse(event.data)

    const { userId, lastChangedAt } = data

    if (userId == null || lastChangedAt == null) {
      throw new Error(
        `Cannot process userId or lastUpdatedAt from websocket. [data=${JSON.stringify(data)}]`
      )
    }

    emit('socket-last-updated-changed', userId, lastChangedAt)
  }

  // Handle WebSocket close
  websocket.onclose = function () {
    console.warn('WebSocket connection closed')
    websocketState.value = 'closed'
  }

  // Handle WebSocket error
  websocket.onerror = function (error) {
    console.error('WebSocket error', error)
  }
})

onUnmounted(() => {
  if (websocket) {
    websocket.close()
  }
})
</script>

<template>
  <div>
    <p v-if="websocketState === 'closed'" class="closed-warning">
      WebSocket connection has been closed!
    </p>
  </div>
</template>

<style scoped>
.closed-warning {
  color: red;
}
</style>
