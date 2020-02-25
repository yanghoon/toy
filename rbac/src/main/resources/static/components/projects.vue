<template>
  <v-row justify="center">
    <v-col sm="10">
      <h2>Projects</h2>

      <v-row>
        <v-col sm="3" class="blue-grey--text text--darken-3">
          <v-text-field dense label="New Project" v-model="form.newProjName">
            <template v-slot:append-outer>
              <v-btn small icon outlined class="blue-grey--text text--darken-2"
                  :color="form.newProjName ? 'primary' : ''"
                  :disabled="!form.newProjName"
                  @click="addProj">
                <v-icon>mdi-plus</v-icon>
              </v-btn>
            </template>
          </v-text-field>
        </v-col>
      </v-row>

      <v-simple-table class="blue-grey darken-3">
        <template v-slot:default>
          <thead class="blue-grey darken-4">
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Groups</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in items" :key="item.name">
              <td>{{ item.name }}</td>
              <td>{{ item.subgroups.length }}</td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>

      <v-progress-linear indeterminate v-if="loading"></v-progress-linear>
    </v-col>
  </v-row>
</template>

<script>
// https://github.com/FranckFreiburger/http-vue-loader/issues/34#issuecomment-375082104
module.exports = {
    data: function() {
      return {
        items: [],
        loading: true,
        realm: 'dummy',
        form: {
          newProjName: ''
        }
      }
    },
    beforeMount: function() {
      this.getProjList();
    },
    methods: {
      'getProjList': function(){
        var vm = this
        var success = function(res){ vm.items = res.data; vm.loading = false; }
        var url = '/companies/' + this.realm + '/projects'

        this.loading = true
        axios.get(url).then(success)
      },
      'addProj': function(){
        var url = '/companies/' + this.realm + '/projects/new?project=' + this.form.newProjName;

        this.form.newProjName = ''
        axios.post(url).then(this.getProjList)
      },
      // 'changeProj': function(item){
      //   var url = '/companies/' + item.name + '?enabled=' + item.enabled;
      //   axios.put(url).then(this.getRealmList)
      // }
    }
  }
</script>