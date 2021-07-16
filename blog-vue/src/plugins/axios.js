import axios from 'axios'
import ElementUI from "element-ui";
import router from '../router/router'
import store from '../store/store'

//axios.defaults.baseURL = "http://venja.top:8081";
axios.defaults.baseURL = "http://localhost:8081";

//  前置拦截
axios.interceptors.request.use(
    config => {
        return config;
    }
)
-
//  后置拦截
axios.interceptors.response.use(
    response => {
        let res = response.data;
        if (res.code === 200) {
            return response;
        } else
        if (res.code === 210) {

            ElementUI.Message.error("Token过期, 重新登陆", {
                duration: 3 * 1000
            });

            location.reload();

            return response;
        }
        return response;
    },
    error => {
        if (error.response.data) {
            error.message = error.response.data.msg;
            if (router.currentRoute.name === "Register") {

            } else {
                router.push("/");
            }
        }

        if (error.response.status === 401) {
            store.commit("REMOVE_INFO");
            router.push("/login");
        }

        ElementUI.Message({
            message: error.message,
            type: "error",
            duration: 1000 * 3,
        });
        return Promise.reject(error)
    }
)