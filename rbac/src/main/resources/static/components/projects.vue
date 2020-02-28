<template>
  <v-row justify="center">
    <v-col sm="10">
      <h2>Projects</h2>

      <v-row>
        <v-col sm="3" class="blue-grey--text text--darken-3">
          <v-text-field dense
              label="+ Add Project"
              hint="Press enter to create"
              v-model="form.newProjName"
              @keyup.enter="addProj">
          </v-text-field>
        </v-col>
      </v-row>

      <v-simple-table class="blue-grey darken-3">
        <template v-slot:default>
          <thead class="blue-grey darken-4">
            <tr>
              <th class="text-left">Name</th>
              <th class="text-left">Groups</th>
              <th class="text-left">Actions</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="item in items" :key="item.name">
              <td>{{ item.name }}</td>
              <td>{{ item.subgroups.length }}</td>
              <td>
                <v-btn small icon class="blue-grey--text" @click="removeProj(item)">
                  <v-icon>mdi-delete</v-icon>
                </v-btn>
              </td>
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
      'removeProj': function(item){
        var url = '/companies/' + this.realm + '/projects/' + item.name;

        axios.delete(url).then(this.getProjList)
      },
      // 'changeProj': function(item){
      //   var url = '/companies/' + item.name + '?enabled=' + item.enabled;
      //   axios.put(url).then(this.getRealmList)
      // }
    }
  }
</script>