<template>
  <v-layout column px-12>
    <!--
    <v-flex xs12 pa-10 pt-12 class="title">
      <h2>Groups</h2>
    </v-flex>
    -->

    <v-flex xs12 px-4>

      <h1 class="primary--text text-bold">Authority</h1>

      <!-- <v-btn class="secondary">add</v-btn> -->

      <v-tabs mt-10 class="secondary" color="#1173ce" background-color="#e7ecef" v-model="tab">
        <v-tab>Users</v-tab>
        <v-tab>Groups</v-tab>
        <v-tab>
          Join
          <v-chip x-small label class="noti info font-weight-black caption">3</v-chip>
        </v-tab>
      </v-tabs>
      <v-divider></v-divider>
    </v-flex>

    <v-flex xs12 pt-5>
      <v-card>

        <v-card-text class="pt-3">
          <v-simple-table>
            <template v-slot:default>
              <thead>
                <tr>
                  <th v-for="(v, k) in items[0]" :key="k" class="text-left text-uppercase">{{ k }}</th>
                  <!--
                  <th class="text-left text-uppercase">Name</th>
                  <th class="text-left text-uppercase">Email</th>
                  -->
                  <th class="text-left text-uppercase">Roles</th>
                  <th class="text-left text-uppercase">Actions</th>
                </tr>
              </thead>
              <tbody>
                <template>
                  <tr v-for="item in items" :key="item.id">
                    <td v-for="v in item" :key="v">{{ v }}</td>
                    <td>
                      <v-select :items="raw[2]" item-text="name" placeholder="Roles" dense falt hide-details class="body-2"></v-select>
                    </td>
                    <td>
                      <!-- <v-btn small icon class="secondary" dark> -->
                      <!-- <v-btn small icon class="grey lighten-2 grey--text" dark>
                        <v-icon class="title">mdi-pencil</v-icon>
                      </v-btn> -->
                      <v-btn small icon class="grey lighten-2 grey--text" dark>
                        <v-icon class="title">mdi-delete</v-icon>
                      </v-btn>
                      <!-- <v-btn small icon class="grey lighten-2 grey--text" dark>
                        <v-icon class="title">mdi-delete</v-icon>
                      </v-btn> -->
                    </td>
                  </tr>
                </template>
              </tbody>
            </template>
          </v-simple-table>

         <v-divider></v-divider>

          <v-row no-gutters align="start" justify="space-between" class="pt-4 px-4">
            <v-col cols="2" class="pt-2">
              <span class="pg-label">1 - 7 of </span>
              <span class="body-2 secondary--text font-weight-bold">139</span>
              <span class="pg-label">items </span>
            </v-col>

            <v-col cols="6" class="lighten-1">
              <v-row no-gutters align="center" justify="center">
                <v-btn icon class="body-2 pg-label">
                  <v-icon>mdi-chevron-left</v-icon>
                </v-btn>
                <!-- <v-btn class="body-2 secondary" x-small dark fab depressed>1</v-btn> -->
                <v-btn icon class="body-2 secondary--text font-weight-bold">1</v-btn>
                <v-btn icon class="pg-label">2</v-btn>
                <v-btn icon class="pg-label">...</v-btn>
                <v-btn icon class="pg-label">7</v-btn>
                <v-btn icon class="pg-label">8</v-btn>
                <v-btn icon class="pg-label">
                  <v-icon>mdi-chevron-right</v-icon>
                </v-btn>
              </v-row>
            </v-col>

            <v-col cols="2">
              <v-row no-gutters justify="end">
                <v-col class="pt-2">
                  <span class="pg-label">Rows per page:</span>
                </v-col>
                <v-col>
                  <v-select dense falt hide-details
                    class="body-2"
                    :items="pg" color="#1173ce">
                  </v-select>
                </v-col>
              </v-row>
            </v-col>
          </v-row>

        </v-card-text>

      </v-card>
    </v-flex>

    

  </v-layout>
</template>

<script>
export default {
  data: function() {
    return {
      tab: 0,
      raw: [
        [
          {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
          {"id":"666b32a7-03a7-4e92-940c-ecf371feeafa","name":"cloudzcp-admin","email":"cloudzcp-admin@sk.com"},
          {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
          {"id":"666b32a7-03a7-4e92-940c-ecf371feeafa","name":"cloudzcp-admin","email":"cloudzcp-admin@sk.com"},
          {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
          {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
          {"id":"666b32a7-03a7-4e92-940c-ecf371feeafa","name":"cloudzcp-admin","email":"cloudzcp-admin@sk.com"},
          {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
          {"id":"666b32a7-03a7-4e92-940c-ecf371feeafa","name":"cloudzcp-admin","email":"cloudzcp-admin@sk.com"},
          {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"admin","email":"admin@sk.com"},
        ],
        [
          {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"group-admin","users":"2"},
          {"id":"666b32a7-03a7-4e92-940c-ecf371feeafa","name":"group-editor","users":"4"},
          {"id":"f75600b8-2714-4c54-8d08-1ebe6fbef5a0","name":"group-viewer","users":"12"},
        ],
        [
          {"name":"admin","users":"2"},
          {"name":"editor","users":"2"},
          {"name":"viewer","users":"2"}
        ]
      ],
      pg: [5, 10, 20, 30],
      color: {
        secondary: '#1173ce'
      }
    }
  },
  computed: {
    items(){
      return this.raw[this.tab] || this.raw[0]
    }
  }
}
</script>
<style scoped>
/* .title { background-color: inherit !important; } */
/* .title { background-color: #fff; } */

/* .v-data-table thead tr { background-color: #e7ecef; } */
/* .v-data-table thead tr { background-color: #0f4c81; }
.v-data-table thead tr th { color: #fff; font-weight: bold; } */
/* .v-data-table thead tr th { color: rgba(0, 0, 0, 0.3); font-weight: bold; } */
.v-data-table thead tr th { color: #0f4c81; font-weight: bold; }

.v-tabs { margin-left: -15px; margin-top: 5px; background-color: #e7ecef !important; }
/* .v-tabs { margin: 0px; } */

.pg-label { color: #9e9e9e !important; }

/* .noti { margin-left: 5px; padding: 3px 4px 0 5px; vertical-align: middle; letter-spacing: 0em; } */
.noti { margin-left: 7px; padding: 0px 4px; }
</style>
