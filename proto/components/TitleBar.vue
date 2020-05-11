<template>
    <v-col cols="12" px-4>

      <h1 class="primary--text text-bold text-capitalize">{{ $route.params.kind }}</h1>

      <!-- <v-btn class="secondary">add</v-btn> -->

      <v-tabs mt-10 color="#1173ce" background-color="#e7ecef" v-model="tab">
        <v-tab class="font-weight-black">Users</v-tab>
        <v-tab class="font-weight-black">Groups</v-tab>
        <v-tab class="font-weight-black">
          Join
          <v-chip x-small label class="noti info font-weight-black caption">3</v-chip>
        </v-tab>
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
.v-tabs { margin-left: -15px; margin-top: 5px; background-color: #e7ecef !important; }
.noti { margin-left: 7px; padding: 0px 4px; }
</style>
