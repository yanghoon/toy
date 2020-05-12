<template>
  <v-row column class="pl-4 pr-12 mt-n7">
    <v-col cols="12">
      <TitleBar />
    </v-col>

    <v-col cols="5" class="pa-0 pl-6">
      <SearchBar />
    </v-col>

    <v-col cols="12" class="pl-6">
      <Table />
    </v-col>
  </v-row>
</template>

<script>
import TitleBar from '~/components/TitleBar.vue'
import SearchBar from '~/components/SearchBar.vue'
import Table from '~/components/Table.vue'

export default {
  components: { Table, SearchBar, TitleBar },
  data: function() {
    return {
      tab: 0
    }
  }
}
</script>
