import router from "../router/router";
import { Message } from 'element-ui';


router.beforeEach(
    (to, from, next) => {

        if (to.matched.some(record => record.meta.requireAuth)) {

            const token = this.$store.getters.getToken;

            if (token) {
                if (to.path === '/login') {

                } else {
                    next();
                }
            } else {
                Message({
                    message: "请先登录后尝试访问",
                    type: "warning",
                })
                next({
                    path: '/login'
                })
            }

        } else {
            next();
        }

        if (to.matched.some(record => record.meta.validationCheck)) {

            let targetId = to.path.replace("/blog/", "");
            let n = Number(targetId);
            if (!isNaN(n))
            {

            } else {
                Message({
                    message: "访问页不存在",
                    type: "error",
                })
                next({
                    path: '/'
                })
            }


        } else {
            next();
        }


    }
)