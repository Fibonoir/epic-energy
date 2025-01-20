<template>
    <div class="flex min-h-screen items-center justify-center bg-gray-100">
      <div class="w-full max-w-sm bg-white p-6 rounded shadow">
        <h1 class="text-2xl mb-4 font-bold text-center">Login</h1>
        <form @submit.prevent="onLogin">
          <div class="mb-4">
            <label class="block text-gray-700">Username</label>
            <input
              v-model="form.username"
              type="text"
              class="border rounded w-full px-3 py-2"
              required
            />
          </div>
          <div class="mb-4">
            <label class="block text-gray-700">Password</label>
            <input
              v-model="form.password"
              type="password"
              class="border rounded w-full px-3 py-2"
              required
            />
          </div>
          <button
            type="submit"
            class="bg-blue-500 w-full text-white font-semibold px-4 py-2 rounded hover:bg-blue-600"
          >
            Login
          </button>
          <p class="mt-4">
            Don't have an account?
            <NuxtLink to="/signup" class="text-blue-500 hover:text-blue-600">Sign Up</NuxtLink>
          </p>
        </form>
      </div>
    </div>
  </template>
  
  <script setup lang="ts">
  import { ref } from 'vue'
  import { useRouter } from 'vue-router'
  import { useAuth } from '~/composables/useAuth'
  
  const router = useRouter()
  const { login } = useAuth()
  
  const form = ref({
    username: '',
    password: ''
  })
  
  const onLogin = async () => {
    try {
      await login(form.value)
      router.push('/')  // navigate to home or any protected page
    } catch (err: any) {
      alert(err.message || 'Login failed')
    }
  }
  </script>
  