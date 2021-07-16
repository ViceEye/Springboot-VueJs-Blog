<template>
  <div>
    <el-card class="ledger-item-container">
      <div class="ledger-item-icon">
        <i class="el-icon-chicken white"></i>
      </div>
      <div class="ledger-item-note">
        吃饭
      </div>
      <div class="ledger-item-detail">
        <div class="ledger-item-amount bold">
          ¥ 2.00
        </div>
        <div class="ledger-item-date grey">
          2021-07-10 1:58
        </div>
      </div>
    </el-card>
  </div>
</template>

<script>
export default {
  name: "LedgerItem"
}
</script>

<style scoped>
.ledger-item-container {
  min-height: 90px;
  text-align: left;
}
.ledger-item-icon {
  margin: 8px 10px 8px 0;
  height: 34px;
  width: 34px;
  border-radius: 50%;
  background-color: #f56c6c;
  display: inline-flex;
  align-items: center;
  justify-content: center;
}
.ledger-item-note {
  display: inline-flex;
}
.ledger-item-detail {
  text-align: right;
  float: right;
}
.ledger-item-amount {
  padding: 4px 0;
}
.ledger-item-date {
  padding: 4px 0;
}
</style>