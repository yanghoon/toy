<template>
  <v-layout column>
    <!--
    <v-flex xs12 pa-10 pt-12 class="title">
      <h2>Groups</h2>
    </v-flex>
    -->

    <v-flex xs12 px-10>

      <h2 class="primary--text">Authority</h2>

      <v-tabs mt-10 class="secondary" color="#1173ce" background-color="#e7ecef">
        <v-tab>Assignments</v-tab>
        <v-tab>Groups</v-tab>
        <v-tab>Roles</v-tab>
      </v-tabs>

      <v-divider></v-divider>
    </v-flex>

    <v-flex xs12 px-10 pt-8>
      <v-card>
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left text-uppercase">Name</th>
              <th class="text-left text-uppercase">Email</th>
              <th class="text-left text-uppercase">Roles</th>
              <th class="text-left text-uppercase">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in items" :key="item.name">
              <td>{{ item.name }}</td>
              <td>{{ item.email }}</td>
              <td>
                <v-select :items="roles" placeholder="Roles" dense falt hide-details class="caption"></v-select>
              </td>
              <td>
                <v-btn small icon class="primary">
                  <v-icon style="color: white;">mdi-delete</v-icon>
                </v-btn>
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
      </v-card>
      
    </v-flex>
  </v-layout>
</template>

<script>
export default {
  data: function() {
    return {
      items: [
        {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
        {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
        {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
        {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
        {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
        {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
        {"id":"666b32a7-03a7-4e92-940c-ecf371feeafa","name":"cloudzcp-admin","email":"cloudzcp-admin@sk.com"}
      ]
    }
  }
}
</script>
<style scoped>
/* .title { background-color: inherit !important; } */
/* .title { background-color: #fff; } */

/* .v-data-table thead tr th { color: #4cc9df !important; } */
.v-data-table thead tr { background-color: #e7ecef; }
/* .v-data-table thead tr th { color: #fff; font-weight: bold; } */

.v-tabs { margin-left: -15px; margin-top: 5px; background-color: #e7ecef !important; }
/* .v-tabs { margin: 0px; } */
</style>
