import type { NavigationGuard } from 'vue-router'
import { useAuth } from '~/composables/useAuth'


export default <NavigationGuard> function (to, from) {
  // Example: only protect routes starting with /clients
  if (to.path.startsWith('/clients')) {
    const { getToken } = useAuth()
    if (!getToken()) {
      return '/login'
    }
  }
}