import {
  createRouter,
  createWebHistory,
  type NavigationGuardNext,
  type RouteLocationNormalized,
  type RouteLocationNormalizedLoaded
} from 'vue-router'
import ProductsView from '../views/ProductsView.vue'
import { useSessionStore } from '@/stores/session-store'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: ProductsView,
      beforeEnter: [checkUserAuthentication]
    },
    {
      path: '/login',
      name: 'login',
      component: () => import('../views/LoginView.vue')
    }
  ]
})

function checkUserAuthentication(
  _: RouteLocationNormalized,
  __: RouteLocationNormalizedLoaded,
  next: NavigationGuardNext
) {
  const sessionStore = useSessionStore()

  if (sessionStore.isUserLoggedIn) {
    next()
  }

  console.warn(
    'User tried to access protected route, but was not authenticated - redirecting to login page.'
  )

  return next({ name: 'login' })
}

export default router
