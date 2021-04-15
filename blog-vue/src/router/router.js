import Vue from 'vue';
import VueRouter from 'vue-router';
import BlogDetail from "../components/BlogDetail";
import BlogEdit from "../views/BlogEdit";
import Blogs from "../views/Blogs";
import Login from "../views/Login";
import Register from "../views/Register";

Vue.use(VueRouter);

const routes = [
  {
    path: '/',
    name: 'Index',
    component: Blogs
  },
  {
    path: '/blogs',
    name: 'Blogs',
    component: Blogs,
    redirect: {name: 'Index'}
  },
  {
    path: '/login',
    name: 'Login',
    component: Login
  },
  {
    path: '/register',
    name: 'Register',
    component: Register
  },
  {
    path: '/blog/:blogId/edit',
    name: 'BlogEdit',
    component: BlogEdit,
    meta: {
      requireAuth: true,
    }
  },
  {
    path: '/blog/add',
    name: 'BlogAdd',
    component: BlogEdit,
    meta: {
      requireAuth: true,
    }
  },
  {
    path: '/blog/:blogId',
    name: 'BlogDetail',
    component: BlogDetail,
    meta: {
      validationCheck: true,
    }
  },
]

const router = new VueRouter( {
  mode: 'history',
  base: process.env.BASE_URL,
  routes
})

export default router
