<template>
  <div>
    <Nav></Nav>

    <div class="bg-img"></div>
    <div class="ledger-container no-select" :class="{'mobile-80': true, 'mobile-90': true}">

      <el-card class="ledger-container-card bg-purple-dark" shadow="hover">
        <div class="ledger-container-summary">

          <div class="ledger-summary-title grey">
            本月支出
          </div>

          <div class="ledger-summary-amount black">
            <span v-show=!hide>¥ 1000.00</span>
            <span class="large-hide-star" v-show=hide>****</span>
            <div class="ledger-summary-hide-button grey" :class="{'flash-bg': flashing}"><div class="ledger-summary-hide"><div v-on:click="switchIcon()" class="ledger-summary-button">
              <i :class="hideButton"></i>
            </div></div></div>
          </div>

          <div class="ledger-summary-title grey">
            <div style="display: inline-block; float: left">本月收入</div>
            <div class="black bold" style="display: inline-block; padding-left: 5px; float: left">
              <span v-show=!hide>¥ 1000.00</span>
              <span class="large-hide-star" v-show=hide>****</span>
            </div>
            <div class="black bold" style="display: inline-block; float: right; padding-left: 10px">
              <span v-show=!hide>未设置预算</span>
              <span class="large-hide-star" v-show=hide>****</span>
            </div>
            <div style="display: inline-block; float: right">预算剩余</div>
          </div>

        </div>
      </el-card>

      <router-link class="ledger-add-button" to="/ledger/add">
        <el-button round type="danger">记一笔</el-button>
      </router-link>

    </div>

    <div class="ledger-container ledger-margin-top" :class="{'mobile-80': true, 'mobile-90': true}">
      <LedgerItem class="ledger-item-spacing"></LedgerItem>
      <LedgerItem class="ledger-item-spacing"></LedgerItem>
      <LedgerItem class="ledger-item-spacing"></LedgerItem>
      <LedgerItem class="ledger-item-spacing"></LedgerItem>
      <LedgerItem class="ledger-item-spacing"></LedgerItem>
      <LedgerItem class="ledger-item-spacing"></LedgerItem>
    </div>

    <Footer></Footer>
  </div>
</template>

<script>
import Nav from "../components/Nav";
import Footer from "../components/Footer";
import LedgerItem from "./LedgerItem";
export default {
  name: "Ledger",
  components: {
    LedgerItem,
    Footer,
    Nav
  },
  data() {
    return {
      hide: false,
      flashing: false,
      hideButton: 'el-icon-view',
      count: 0
    }
  },
  methods: {
    switchIcon() {
      this.flashBackground();
      if (this.hideButton === 'el-icon-view') {
        this.hideButton = 'el-icon-minus'
        this.hide = true;
      } else {
        this.hideButton = 'el-icon-view';
        this.hide = false;
      }
    },
    flashBackground() {
      this.flashing = true;
      setTimeout(() => this.flashing = false, 100);
    },
    load () {
      this.count += 2
    }
  }
}
</script>

<style scoped>
.bg-img {
  margin-top: 0;
  width: 100%;
  height: 100%;
  background-image: url(../assets/home-page-bg.jpg);
  background-size: cover;
  background-position: center;
  background-repeat: no-repeat;
  background-attachment:fixed;
  position: absolute;
  display: flex;
  z-index: -1;
}
.flash-bg {
  background-color: #f5f5f5;
  transition: background-color 0.1s ease-in-out;
}
.bg-purple-dark {
  background: #f5f5f9;
}
.ledger-container {
  margin: 20px auto 0;
  width: 60%;
}
.ledger-margin-top {
  margin-top: 80px;
}
.ledger-container-card {
  min-height: 142px;
  display: block;
}
.ledger-container-summary {
  text-align: justify;
  font-size: 13px;
}
.ledger-summary-title {
  padding: 5px 0;
}
.ledger-summary-amount {
  padding: 5px 0;
  font-size: 30px;
  font-weight: bold;
}
.ledger-summary-hide-button {
  float: right;
  border-radius: 50%;
}
.ledger-summary-hide {
  display: inline-flex;
  width: 1.2em;
  height: 1.2em;
  line-height: 24px;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
}
.ledger-summary-button {
  opacity: 100%;
  width: 24px;
  height: 24px;
  font-size: 24px;
  border-radius: 50%;
  cursor: pointer;
}
.large-hide-star {
  font-size: 20px;
}
.ledger-add-button {
  text-decoration: none;
  margin: 20px 3px;
  float: right;
}


.ledger-item-spacing {
  margin-bottom: 20px;
}
.ledger-item-spacing:last-child {
  margin-bottom: 0;
}
</style>