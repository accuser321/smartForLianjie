<template>
  <div>
    <div class="scroll">
      <img :src="ossurl + website" />
    </div>
    <!-- <bottomtag class="bottomtag"></bottomtag> -->
  </div>
</template>
<script>
import { getStorage } from '@/lib/util'
export default {
  data () {
    return {
      ossurl: '',
      website: ''
    }
  },
  created () {
    this.ossurl = getStorage('ossurl', '')
    this.website = getStorage('website', '')
  }
}
</script>
<style lang="stylus" scoped>
.scroll {
  overflow-y: auto;

  img {
    width: 100%;
    height: auto;
  }
}

// .bottomtag {
//   position: fixed;
//   z-index: 100;
//   bottom: 0;
//   width: 100%;
// }
</style>
