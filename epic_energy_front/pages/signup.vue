<template>
    <div class="flex min-h-screen items-center justify-center bg-gray-100">
      <div class="w-full max-w-sm bg-white p-6 rounded shadow">
        <h1 class="text-2xl mb-4 font-bold text-center">Sign Up</h1>
        <form @submit.prevent="onSignup">
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
            <label class="block text-gray-700">Email</label>
            <input
              v-model="form.email"
              type="email"
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
          <div class="mb-4">
            <label class="block text-gray-700">Role (optional)</label>
            <input
              v-model="form.role"
              type="text"
              placeholder="admin or leave empty"
              class="border rounded w-full px-3 py-2"
            />
          </div>
            <div class="mb-4">
                <label class="block text-gray-700">Admin Key (optional)</label>
                <input
                v-model="form.adminKey"
                type="password"
                placeholder="admin key or leave empty"
                class="border rounded w-full px-3 py-2"
            />
            </div>
          <button
            type="submit"
            class="bg-blue-500 w-full text-white font-semibold px-4 py-2 rounded hover:bg-blue-600"
          >
            Sign Up
          </button>
          <p class="mt-4">
            Already have an account?
            <NuxtLink to="/login" class="text-blue-500 hover:text-blue-600">Login</NuxtLink>
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
  const { signup } = useAuth()
  
  const form = ref({
    username: '',
    email: '',
    password: '',
    role: '',
    adminKey: '',
  })
  
  const onSignup = async () => {
    try {
      await signup(form.value)
      alert('Signup successful. You can now login.')
      router.push('/login')
    } catch (err: any) {
      alert(err.message || 'Signup failed')
    }
  }
  </script>
  