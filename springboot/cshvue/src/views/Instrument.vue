<template>
  <div>
    <div style="margin: 10px 0">
      <el-input style="width: 200px" placeholder="请输入名称" suffix-icon="el-icon-search" v-model="name"></el-input>
      <el-button class="ml-5" type="primary" @click="load">搜索</el-button>
      <el-button type="warning" @click="reset">重置</el-button>
    </div>
    <div style="margin: 10px 0" >
<!--      <el-upload action="http://localhost:9090/instrument/upload" :show-instrument-list="false" :on-success="handleInstrumentUploadSuccess" style="display: inline-block">-->
<!--        <el-button type="primary" class="ml-5">上传文件 <i class="el-icon-top"></i></el-button>-->
<!--      </el-upload>-->
<!--      <template slot-scope="scope">-->
<!--        -->
<!--      </template>-->
      <el-button type="primary" @click="handleAdd()">新增 <i class="el-icon-circle-plus-outline"></i></el-button>
      <el-popconfirm
          class="ml-5"
          confirm-button-text='确定'
          cancel-button-text='我再想想'
          icon="el-icon-info"
          icon-color="red"
          title="您确定批量删除这些数据吗？"
          @confirm="delBatch"
      >
        <el-button type="danger" slot="reference">批量删除 <i class="el-icon-remove-outline"></i></el-button>
      </el-popconfirm>

    </div>
    <el-table :data="tableData" border stripe :header-cell-class-name="'headerBg'"  @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55"></el-table-column>



      <el-table-column prop="name" label="仪器名称" width="80"></el-table-column>
      <el-table-column prop="createTime" label="创建时间"></el-table-column>
      <el-table-column prop="address" label="存放位置"></el-table-column>

      <el-table-column label="仪器图片">
        <template slot-scope="scope">
          <img :src="scope.row.avatarUrl" alt=""
               style="width: 150px;position: relative; top: 5px; right: -10px">
        </template>
      </el-table-column>
      <el-table-column prop="state" label="是否可借" width="80">
        <template slot-scope="scope">
          <el-switch v-model="scope.row.state" active-color="#13ce66" inactive-color="#ccc" @change="changeEnable(scope.row)"></el-switch>

        </template>
      </el-table-column>
      <el-table-column prop="borrowerId" label="借仪器者ID" width="100"></el-table-column>
      <el-table-column label="操作"   align="center">

        <template slot-scope="scope">
          <el-button type="success" @click="handleEdit(scope.row)">编辑 <i class="el-icon-edit"></i></el-button>
          <el-popconfirm
              class="ml-5"
              confirm-button-text='确定'
              cancel-button-text='我再想想'
              icon="el-icon-info"
              icon-color="red"
              title="您确定删除吗？"
              @confirm="del(scope.row.id)"
          >

            <el-button type="danger" slot="reference">删除 <i class="el-icon-remove-outline"></i></el-button>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <div style="padding: 10px 0">
      <el-pagination
          @size-change="handleSizeChange"
          @current-change="handleCurrentChange"
          :current-page="pageNum"
          :page-sizes="[2, 5, 10, 20]"
          :page-size="pageSize"
          layout="total, sizes, prev, pager, next, jumper"
          :total="total">
      </el-pagination>
    </div>

    <el-dialog title="仪器信息" :visible.sync="dialogFormVisible" width="30%">
    <div style="text-align: center">
      <el-upload
          class="avatar-uploader"
          action="http://localhost:9090/file/upload"
          :show-file-list="false"
          :on-success="handleAvatarSuccess"
      >
        <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">
        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-upload>
    </div>
<!--      </el-upload>-->
<!--      <el-form label-width="80px" size="small">-->
<!--        <el-upload-->
<!--            class="avatar-uploader"-->
<!--            action="http://localhost:9090/file/upload"-->
<!--            :show-file-list="false"-->
<!--            :on-success="handleAvatarSuccess"-->
<!--        >-->
<!--          <img v-if="form.avatarUrl" :src="form.avatarUrl" class="avatar">-->
<!--          <i v-else class="el-icon-plus avatar-uploader-icon"></i>-->
<!--        </el-upload>-->
      <el-form label-width="80px" size="small">
        <el-form-item label="仪器名称">
          <el-input v-model="form.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-input v-model="form.createTime" autocomplete="off"></el-input>
        </el-form-item>
<!--        <el-form-item label="仪器图片">-->
<!--          <el-input v-model="form.avatarUrl" autocomplete="off"></el-input>-->
<!--        </el-form-item>-->
<!--        <el-upload action="http://localhost:9090/file/upload" :show-file-list="false" :on-success="handleInstrumentUploadSuccess" style="display: inline-block">-->
<!--          <el-button type="primary" class="ml-5">上传文件 <i class="el-icon-top"></i></el-button>-->
<!--        </el-upload>-->
        <el-form-item label="存放位置">
          <el-input v-model="form.address" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否可借">
          <el-input v-model="form.state" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="借者ID">
          <el-input v-model="form.borrowerId" autocomplete="off"></el-input>
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
export default {
  name: "instrument",
  data() {
    return {
      dialogFormVisible: false,
      form: {},
      tableData: [],
      name: '',
      multipleSelection: [],
      pageNum: 1,
      pageSize: 10,
      total: 0
    }
  },
  created() {
    this.load()
  },
  methods: {
    load() {
      this.request.get("/instrument/page", {
        params: {
          pageNum: this.pageNum,
          pageSize: this.pageSize,
          name: this.name,
        }
      }).then(res => {
        this.tableData = res.data.records
        this.total = res.data.total
      })
    },

    save() {
      this.request.post("/instrument", this.form).then(res => {
        if (res.code === '200') {
          this.$message.success("保存成功")
          this.dialogFormVisible = false
          this.load()
        } else {
          this.$message.error("保存失败")
        }
      })
    },

    changeEnable(row) {
      this.request.post("/instrument/update", row).then(res => {
        if (res.code === '200') {
          this.$message.success("操作成功")
        }
      })
    },
    del(id) {
      this.request.delete("/instrument/" + id).then(res => {
        if (res.code === '200') {
          this.$message.success("删除成功")
          this.load()
        } else {
          this.$message.error("删除失败")
        }
      })
    },

    handleAdd(row) {
      this.form = row
      this.dialogFormVisible = true

    },
    handleEdit(row) {
      this.form = row
      this.dialogFormVisible = true
    },


    handleSelectionChange(val) {
      console.log(val)
      this.multipleSelection = val
    },
    delBatch() {
      let ids = this.multipleSelection.map(v => v.id)  // [{}, {}, {}] => [1,2,3]
      this.request.post("/instrument/del/batch", ids).then(res => {
        if (res.code === '200') {
          this.$message.success("批量删除成功")
          this.load()
        } else {
          this.$message.error("批量删除失败")
        }
      })
    },
    reset() {
      this.name = ""
      this.load()
    },
    handleSizeChange(pageSize) {
      console.log(pageSize)
      this.pageSize = pageSize
      this.load()
    },
    handleCurrentChange(pageNum) {
      console.log(pageNum)
      this.pageNum = pageNum
      this.load()
    },
    handleInstrumentUploadSuccess(res) {
      console.log(res)
      this.load()
    },
    download(url) {
      window.open(url)
    },
    handleAvatarSuccess(res) {
      this.form.avatarUrl = res
    }
  }
}
</script>

<style scoped>

</style>