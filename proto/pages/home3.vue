<template>
  <Table class="tb"></Table>
</template>

<script>
import Table from '~/components/Table.vue'

export default {
  components: { Table },
  layout: 'default3',
  data: function() {
    return {}
  }
}
</script>
<style scoped>
.tb { padding-top: 32px; }
</style>