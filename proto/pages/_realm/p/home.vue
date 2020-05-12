<template>
  <v-row column class="px-12 mt-n8">
    <v-col cols="12">
      <TitleBar />
    </v-col>

    <!--
    <v-col cols="7">
      <SearchBar />
    </v-col>
    -->

    <v-col cols="12">
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
