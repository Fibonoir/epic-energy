<template>
    <div class="p-4">
      <h2 class="text-2xl font-bold mb-4">Clients</h2>
      <div class="space-y-2">
        <div
          v-for="client in clients"
          :key="client.id"
          class="border rounded p-2 bg-white shadow"
        >
          {{ client.companyName }} (VAT: {{ client.vatNumber }})
        </div>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref, onMounted } from 'vue'
  import { useRuntimeConfig } from '#app'
  import { useAuth } from '~/composables/useAuth'
  
  interface Client {
    id: number
    companyName: string
    vatNumber: string
    // ... other fields
  }
  
  const config = useRuntimeConfig()
  const clients = ref<Client[]>([])
  const { getToken } = useAuth()
  
  onMounted(async () => {
    try {
      const { data } = await useFetch('/api/clients', {
        baseURL: config.public.apiBase,
        headers: {
          Authorization: `Bearer ${getToken()}`
        }
      })
      if (data.value && data.value.content) {
        clients.value = data.value.content
      }
    } catch (err) {
      console.error('Error fetching clients', err)
    }
  })
  </script>
  