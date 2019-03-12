import mongoose, { Schema } from 'mongoose'
const uploadService = require('../../services/upload')

const photoSchema = new Schema({
  noticia: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Noticia'
  },
  imgurLink: {
    type: String
  },
  deleteHash: {
    type: String
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})
photoSchema.pre('remove', { query: true }, function (next) {
  uploadService.deleteImage(this.deletehash)
  return next()
})

photoSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      noticia: this.noticia,
      imgurLink: this.imgurLink,
      deleteHash: this.deleteHash,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Photo', photoSchema)

export const schema = model.schema
export default model
