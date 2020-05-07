<template>
  <v-layout column>
    <!--
    <v-flex xs12 pa-10 pt-12 class="title">
      <h2>Groups</h2>
    </v-flex>
    -->

    <v-flex xs12 px-10 pt-8 class="title">

      <h2 class="primary--text">Authority</h2>

      <v-tabs mt-10 class="secondary" color="#1173ce" background-color="#e7ecef">
        <v-tab>Assignments</v-tab>
        <v-tab>Groups</v-tab>
        <v-tab>Roles</v-tab>
      </v-tabs>

      <v-divider></v-divider>
    </v-flex>

    <v-flex xs12 px-10 pt-10>
      <v-card>
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Email</th>
              <th class="text-left">Roles</th>
              <th class="text-left">Actions</th>
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
      
      <v-flex xs12 py-12></v-flex>

      <!--
      <v-card>
      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Email</th>
              <th class="text-left">Roles</th>
              <th class="text-left">Actions</th>
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
      -->

    </v-flex>
  </v-layout>
</template>

<script>
import Logo from '~/components/Logo.vue'
import VuetifyLogo from '~/components/VuetifyLogo.vue'

export default {
  layout: 'default3',
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
.v-data-table thead tr { background-color: #0f4c81; }
.v-data-table thead tr th { color: #fff; font-weight: bold; }

.v-tabs { margin-left: -14px; margin-top: 15px; background-color: #e7ecef !important; }
/* .v-tabs { margin: 0px; } */
</style>
