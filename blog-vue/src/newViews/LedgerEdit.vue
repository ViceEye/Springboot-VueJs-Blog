<template>
  <div>
    <Nav></Nav>

      <span class="ledger-edit-out t2 bold" v-on:click="test1">支出</span>

    <transition
        v-on:before-enter="beforeEnter"
        v-on:enter="enter"
        v-on:leave="leave"
        v-bind:css="false"
    >
      <p v-if="show">
        Demo
      </p>
    </transition>
    <span class="ledger-edit-in t5 bold">收入</span>

    {{name}}
    <IconSelector></IconSelector>

    <Footer></Footer>
  </div>
</template>

<script>
import Nav from "../components/Nav";
import Footer from "../components/Footer";
import IconSelector from "./IconSelector";
import Velocity from "../plugins/velocity.min.js"
export default {
  name: "LedgerAdd",
  components: {
    IconSelector,
    Nav,
    Footer,
  },
  data() {
    return {
      name: 'test',
      show: false
    }
  },
  methods: {
    setName(name) {
      this.name = name;
    },
    test1() {
      console.log(1);
      this.show = !this.show;
    },
    beforeEnter: function (el) {
      el.style.opacity = 0
      el.style.transformOrigin = 'left'
    },
    enter: function (el, done) {
      Velocity(el, { opacity: 1, fontSize: '1.4em' }, { duration: 300 })
      Velocity(el, { fontSize: '1em' }, { complete: done })
    },
    leave: function (el, done) {
      Velocity(el, { translateX: '15px', rotateZ: '50deg' }, { duration: 600 })
      Velocity(el, { rotateZ: '100deg' }, { loop: 2 })
      Velocity(el, {
        rotateZ: '45deg',
        translateY: '30px',
        translateX: '30px',
        opacity: 0
      }, { complete: done })
    }
  },
  mounted() {
    this.$EventBus.$on("icon_clicked", (val) => {
      this.setName(val);
    })
  }
}
</script>

<style scoped>
.ledger-edit-out {
  padding: 5px;
  user-select: none;
}
.ledger-edit-in {
  user-select: none;
}
</style>