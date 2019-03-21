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
      noticia.save()
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
  Noticia.find(query, select, cursor)
    .then((noticias) => ({
      count: noticias.length,
      rows: noticias.map((noticia) => noticia.view())
    }))
    .then(success(res))
    .catch(next)
}

export const indexGeo = ({ querymen: { query, select, cursor }, params }, res, next) => {
  Noticia.find({
    localizacion: {
      $near: {
        $maxDistance: params.maxDistance || 1000,
        $geometry: {
          type: 'Point',
          coordinates: [params.long, params.lat]
        }
      }
    }
  }, select, cursor)
    .find((error, results) => {
      if (error) console.log(error)
      results.localizacion = params.long + ',' + params.lat

      res.send({
        count: results.length,
        rows: results.map((results) => results.view())
      })
    })
}

export const show = ({ params }, res, next) => {
  return Noticia.findById(params.id)
    .populate({
      path: 'comentarios'
    }).populate('comentarios.autor')
    .then(noticia => noticia.view(true))
    .then(success(res))
    .catch(next)
}
export const update = ({ bodymen: { body }, params }, res, next) => {
  console.log(body);
  
  Noticia.findById(params.id)
    .then(notFound(res))
    .then((noticia) => noticia ? Object.assign(noticia, body).save() : null)
    .then((noticia) => noticia ? noticia.view(true) : null)
    .then(success(res))
    .catch(next)
}

export const destroy = async ({ params }, res, next) => {
  var autor
  var noticiaId
  await Noticia.findById(params.id)
    .then(notFound(res))
    .then((noticia) => noticia ? noticia.remove() : null)
    .then(noticia => {
      console.log(noticia.autor.id)
      noticiaId = noticia.id

      autor = noticia.autor.id
      console.log('autor ' + autor)

      return noticia
    })
    .then(success(res, 204))
    .catch(next)

  User.findById(autor)
    .then(user => {
      var encontrado = false
      var i = 0
      while (!encontrado && i <= user.noticias.length) {
        if (user.noticias[i].equals(noticiaId)) {
          encontrado = true
        } else {
          i = i + 1
        }
      }

      if (encontrado) {
        user.noticias.splice(i, 1)
        user.save()
      }
    })
}
