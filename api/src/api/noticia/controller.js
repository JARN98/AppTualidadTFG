import { success, notFound } from '../../services/response/'
import { Noticia } from '.'
import { User } from '../user'

export const create = async ({ user, bodymen: { body } }, res, next) => {
  var nuevaNoticia
  await Noticia.create({ ...body })
    .then((noticia) => {
      noticia.autor.id = user.id
      noticia.autor.picture = user.picture
      noticia.autor.name = user.name
      noticia.autor.email = user.email
      nuevaNoticia = noticia.id
      console.log(nuevaNoticia)
      return noticia.view(true)
    })
    .then(success(res, 201))
    .catch(next)

  await User.findById(user.id)
    .then(usuario => {
      usuario.noticias.push(nuevaNoticia)
      usuario.save()
    })
    .catch(next)
}

export const index = ({ querymen: { query, select, cursor } }, res, next) => {


  Noticia.count(query)
    .then(count => Noticia.find(query, select, cursor)
      .then((noticias) => ({
        count,
        rows: noticias.map((noticia) => noticia.view())
      }))
    )
    .then(success(res))
    .catch(next)
}

// export const indexDestacado = ({ querymen: { query, select, cursor } }, res, next) =>
//   Noticia.find()
//     .sort('-likes')
//     .exec(function (err, docs) {
//       if (err) {      
//       }
//       console.log(docs)
//       return docs
//     })
export const show = ({ params }, res, next) => {
  return Noticia.findById(params.id)
    .populate({
      path: 'comentarios'
    }).populate('comentarios.autor')
    .then(noticia => noticia.view(true))
    .then(success(res))
    .catch(next)
}
export const update = ({ bodymen: { body }, params }, res, next) =>
  Noticia.findById(params.id)
    .then(notFound(res))
    .then((noticia) => noticia ? Object.assign(noticia, body).save() : null)
    .then((noticia) => noticia ? noticia.view(true) : null)
    .then(success(res))
    .catch(next)

export const destroy = async ({ params }, res, next) => {
  var comentariosDeNoticia
  await Noticia.findById(params.id)
    .then(notFound(res))
    .then((noticia) => noticia ? noticia.remove() : null)
    .then(noticia => {
      comentariosDeNoticia = noticia.comentarios
      console.log(comentariosDeNoticia)
      return noticia
    })
    .then(success(res, 204))
    .catch(next)
}
