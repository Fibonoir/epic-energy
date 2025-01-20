import { ref } from 'vue'
import { useRuntimeConfig } from '#app'

interface LoginPayload {
  username: string
  password: string
}

interface SignUpPayload {
  username: string
  email: string
  password: string
  role?: string
}

const token = ref<string | null>(null)

export function useAuth() {
  const config = useRuntimeConfig()

  const getToken = () => {
    if (!token.value) {
      token.value = localStorage.getItem('jwt') || null
    }
    return token.value
  }

  const setToken = (jwt: string) => {
    token.value = jwt
    localStorage.setItem('jwt', jwt)
  }

  const clearToken = () => {
    token.value = null
    localStorage.removeItem('jwt')
  }

  interface loginResponse {
    token: string,
    id: number,
    username: string,
    email: string,
    roles: [{
      authority: string
    }]
  }
  

  // Sign in
  const login = async (payload: LoginPayload) => {
    const { data, error } = await useFetch<loginResponse>('/api/auth/login', {
      baseURL: config.public.apiBase as string, // "http://localhost:8080" from nuxt.config.ts
      method: 'POST',
      body: payload
    })
    if (error.value) {
      throw new Error(error.value.message || 'Login error')
    }
    // Example backend returns { token, ... }
    // Adjust according to your actual JSON structure
    if (data.value && data.value.token) {
      console.log(JSON.parse(JSON.stringify(data.value)));
      setToken(data.value.token)
    }
  }

  // Sign up
  const signup = async (payload: SignUpPayload) => {
    const { error } = await useFetch('/api/auth/register', {
      baseURL: config.public.apiBase as string,
      method: 'POST',
      body: payload
    })
    if (error.value) {
      throw new Error(error.value.message || 'Signup error')
    }
  }

  const logout = () => {
    clearToken()
  }

  // Authenticated GET example (to fetch user info, etc.)
  const getUserProfile = async () => {
    const { data, error } = await useFetch('/api/users/1', {
      baseURL: config.public.apiBase as string,
      headers: {
        Authorization: `Bearer ${getToken()}`
      }
    })
    if (error.value) {
      throw new Error(error.value.message || 'Cannot fetch user info')
    }
    return data.value
  }

  return {
    getToken,
    login,
    logout,
    signup,
    getUserProfile
  }
}