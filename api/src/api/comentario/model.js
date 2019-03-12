import mongoose, { Schema } from 'mongoose'

const comentarioSchema = new Schema({
  autor: {
    email: {
      type: String
    },
    picture: {
      type: String
    },
    name: {
      type: String
    },
    id: {
      type: mongoose.Schema.Types.ObjectId,
      ref: 'User'
    }
  },
  contenido: {
    type: String
  },
  noticia: {
    type: mongoose.Schema.Types.ObjectId,
    ref: 'Noticia'
  }
}, {
  timestamps: true,
  toJSON: {
    virtuals: true,
    transform: (obj, ret) => { delete ret._id }
  }
})

comentarioSchema.methods = {
  view (full) {
    const view = {
      // simple view
      id: this.id,
      autor: {
        email: this.autor.email,
        picture: this.autor.picture,
        name: this.autor.name,
        id: this.autor.id,
        noticia: this.noticia
      },
      contenido: this.contenido,
      createdAt: this.createdAt,
      updatedAt: this.updatedAt
    }

    return full ? {
      ...view
      // add properties for a full view
    } : view
  }
}

const model = mongoose.model('Comentario', comentarioSchema)

export const schema = model.schema
export default model
