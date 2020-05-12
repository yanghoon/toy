<template>
    <v-col cols="12">
      <h1 class="primary--text display-1 font-weight-medium text-capitalize">{{ $route.params.kind }}</h1>
      <!-- <h1 class="primary--text text-capitalize">{{ $route.params.kind }}</h1> -->

      <v-tabs color="#1173ce" background-color="#e7ecef" v-model="tab">
        <v-tab class="body-2 font-weight-medium">Users</v-tab>
        <v-tab class="body-2 font-weight-medium">Groups</v-tab>
        <v-tab class="body-2 font-weight-medium">
          Join
          <v-chip x-small label class="noti info font-weight-black caption">3</v-chip>
        </v-tab>

        <v-spacer/>
        <v-btn depressed small rounded class="secondary mt-3">
          <v-icon>mdi-plus</v-icon>
          <span class="body-2 pr-2">Add New</span>
        </v-btn>
      </v-tabs>
      <v-divider></v-divider>
    </v-col>
</template>

<script>
export default {
  data: function() {
    return {
      tab: 0
    }
  }
}
</script>
<style scoped>
.v-tabs { background-color: #e7ecef !important; }
.noti { margin-left: 7px; padding: 0px 4px; }
</style>
