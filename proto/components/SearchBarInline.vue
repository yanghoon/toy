<template>
    <!-- 
    <v-col cols="7">
      <v-card class="px-3">
        <v-card-actions class="pa-0">
          <v-text-field placeholder="Search" prepend-icon="mdi-magnify" clearable
              :loading="!!keyword"
              v-model="keyword"
              @focus="searching = true"
              @blur="searching = false">
          </v-text-field>

          <v-btn depressed small class="secondary ml-3">Add</v-btn>
        </v-card-actions>

        <v-row class="pa-0 mt-n1 pb-3 ml-8" v-if="searching">
          <v-btn-toggle dense rounded class="mr-5">
            <v-btn small class="caption">admin</v-btn>
            <v-btn small class="caption">editor</v-btn>
            <v-btn small class="caption">user</v-btn>
          </v-btn-toggle>

          <v-btn-toggle dense rounded class="mr-5">
            <v-btn small class="caption">custom-1</v-btn>
            <v-btn small class="caption">custom-2</v-btn>
            <v-btn small class="caption">custom-3</v-btn>
          </v-btn-toggle>
        </v-row>
      </v-card>
    </v-col>
    -->

    <!--
    <v-col cols="12" class="py-0">
      <v-row align="center" class="px-0">
        <v-col cols="5" class="py-0">
          <v-text-field label="Search" prepend-icon="mdi-magnify" clearable
            v-model="keyword"
            @focus="searching = true"
            @blur="searching = false">
          </v-text-field>
        </v-col>

        <v-col>
          <v-btn depressed small rounded class="secondary">
            <v-icon>mdi-plus</v-icon>
            <span class="body-2 pr-2">Add New</span>
          </v-btn>
        </v-col>
      </v-row>
        <v-row class="pa-0 mt-n3 pb-3 ml-6" v-if="searching">
          <v-btn-toggle dense rounded class="mr-5">
            <v-btn small class="caption">admin</v-btn>
            <v-btn small class="caption">editor</v-btn>
            <v-btn small class="caption">user</v-btn>
          </v-btn-toggle>

          <v-btn-toggle dense rounded class="mr-5">
            <v-btn small class="caption">custom-1</v-btn>
            <v-btn small class="caption">custom-2</v-btn>
            <v-btn small class="caption">custom-3</v-btn>
          </v-btn-toggle>
        </v-row>
      <v-divider />
    </v-col>
    -->

    <v-col cols="12" class="px-0 py-0">
      <v-row align="center" justify="space-between">
        <v-col cols="5" class="ml-3">
            <!--
            <v-text-field placeholder="Search" prepend-inner-icon="mdi-magnify" clearable
                filled rounded
                dense hide-details
                background-color="#e7ecef"
                class="primary--text body-1"
                v-model="keyword"
                @focus="searching = true"
                @blur="searching = false">
            </v-text-field>
            -->
            <v-text-field placeholder="Search" prepend-inner-icon="mdi-magnify" clearable
                hide-details
                color="#41b6e6"
                class="body-1 py-0"
                v-model="keyword"
                @focus="searching = true"
                @blur="searching = false">
            </v-text-field>
        </v-col>
        <v-col cols="12" v-if="false && searching">
            <v-row class="pa-0 mt-n3 ml-8">
              <v-btn-toggle dense rounded class="mr-5">
                <v-btn small class="caption">admin</v-btn>
                <v-btn small class="caption">editor</v-btn>
                <v-btn small class="caption">user</v-btn>
              </v-btn-toggle>

              <v-btn-toggle dense rounded class="mr-5">
                <v-btn small class="caption">custom-1</v-btn>
                <v-btn small class="caption">custom-2</v-btn>
                <v-btn small class="caption">custom-3</v-btn>
              </v-btn-toggle>
            </v-row>
        </v-col>
      </v-row>
      <v-divider />
    </v-col>

</template>

<script>
export default {
  data: function() {
    return {
      searching: false,
      keyword: '',
      
    }
  }
}
</script>
<style scoped>
</style>
